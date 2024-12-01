package com.equipment.system.filter;

import com.alibaba.fastjson.JSON;
import com.equipment.common.result.Result;
import com.equipment.common.result.ResultCodeEnum;
import com.equipment.common.utils.JwtHelper;
import com.equipment.common.utils.ResponseUtil;
import com.equipment.common.utils.UserInfoHelperService;
import com.equipment.model.system.SysUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

        private UserInfoHelperService userInfoHelperService;

        private RedisTemplate redisTemplate;

        public TokenAuthenticationFilter(RedisTemplate redisTemplate,UserInfoHelperService userInfoHelperService) {
            this.redisTemplate = redisTemplate;
            this.userInfoHelperService = userInfoHelperService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            logger.info("url:"+request.getRequestURI());
            //如果是登录接口，直接放行
            if("/admin/system/index/login".equals(request.getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }


            //将逻辑删除以及离职的token进行拦截。
            SysUser sysUser = getSysUser(request);
            if(sysUser == null){
                ResponseUtil.out(response, Result.build(null, ResultCodeEnum.ACCOUNT_ERROR));
            }
            if(sysUser.getStatus() == 0){
                ResponseUtil.out(response, Result.build(null, ResultCodeEnum.ACCOUNT_STOP));
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            if(null != authentication) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } else {
                ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
            }
        }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        logger.info("url:"+request.getRequestURI());
        String token = request.getHeader("token");
        logger.info("token:"+token);
        if (!StringUtils.isEmpty(token)) {
            String userCode = JwtHelper.getUserCode(token);
            logger.info("userCode:"+userCode);
            if (!StringUtils.isEmpty(userCode)) {
                String authoritiesString = (String) redisTemplate.opsForValue().get(userCode);
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (Map map : mapList) {
                    authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
                }
                return new UsernamePasswordAuthenticationToken(userCode, null, authorities);
                // return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            }
        }
        return null;
    }

    private SysUser getSysUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            String userCode = JwtHelper.getUserCode(token);
            return userInfoHelperService.getUserInfo(userCode);
        }
        return null;
    }
}
