"use strict";
var __defProp = Object.defineProperty;
var __defProps = Object.defineProperties;
var __getOwnPropDescs = Object.getOwnPropertyDescriptors;
var __getOwnPropSymbols = Object.getOwnPropertySymbols;
var __hasOwnProp = Object.prototype.hasOwnProperty;
var __propIsEnum = Object.prototype.propertyIsEnumerable;
var __defNormalProp = (obj, key, value) => key in obj ? __defProp(obj, key, { enumerable: true, configurable: true, writable: true, value }) : obj[key] = value;
var __spreadValues = (a, b) => {
  for (var prop in b || (b = {}))
    if (__hasOwnProp.call(b, prop))
      __defNormalProp(a, prop, b[prop]);
  if (__getOwnPropSymbols)
    for (var prop of __getOwnPropSymbols(b)) {
      if (__propIsEnum.call(b, prop))
        __defNormalProp(a, prop, b[prop]);
    }
  return a;
};
var __spreadProps = (a, b) => __defProps(a, __getOwnPropDescs(b));
const common_vendor = require("../../../common/vendor.js");
const tmui_tool_lib_minxs = require("../../tool/lib/minxs.js");
const tmui_tool_lib_tmpinia = require("../../tool/lib/tmpinia.js");
if (!Math) {
  tmText();
}
const tmText = () => "../tm-text/tm-text.js";
const _sfc_main = /* @__PURE__ */ common_vendor.defineComponent({
  __name: "tm-divider",
  props: __spreadProps(__spreadValues({}, tmui_tool_lib_minxs.custom_props), {
    color: {
      type: String,
      default: "grey-3"
    },
    fontColor: {
      type: String,
      default: "grey-1"
    },
    fontSize: {
      type: Number,
      default: 26
    },
    vertical: {
      type: [Boolean],
      default: false
    },
    height: {
      type: [Number, String],
      default: 26
    },
    label: {
      type: String,
      default: ""
    },
    showLabel: {
      type: Boolean,
      default: false
    },
    align: {
      type: String,
      default: "center"
      //left,right,center
    },
    margin: {
      type: Array,
      default: () => [16, 24]
    },
    border: {
      type: [Number],
      default: 1
    },
    //使用原始颜色为线条色，而不使用计算过的颜色值。
    realColor: {
      type: [Boolean],
      default: false
    }
  }),
  emits: ["click"],
  setup(__props, { emit: __emit }) {
    const store = tmui_tool_lib_tmpinia.useTmpiniaStore();
    const props = __props;
    const emits = __emit;
    const borderDir = common_vendor.computed(() => props.vertical ? "left" : "bottom");
    const _label = common_vendor.computed(() => props.label || props.showLabel);
    const tmcfg = common_vendor.computed(() => store.tmStore);
    const _realColor = common_vendor.computed(() => props.realColor);
    const isDark = common_vendor.computed(
      () => tmui_tool_lib_minxs.computedDark(
        __spreadProps(__spreadValues({}, props), {
          borderDirection: borderDir.value
        }),
        tmcfg.value
      )
    );
    const tmcomputed = common_vendor.computed(
      () => tmui_tool_lib_minxs.computedTheme(
        __spreadProps(__spreadValues({}, props), {
          borderDirection: borderDir.value
        }),
        isDark.value,
        tmcfg.value
      )
    );
    function onClick(e) {
      emits("click", e);
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: !_label.value && props.vertical
      }, !_label.value && props.vertical ? {
        b: common_vendor.s({
          backgroundColor: _realColor.value ? tmcomputed.value.color : tmcomputed.value.border
        }),
        c: common_vendor.s(props.vertical ? {
          width: props.border + "rpx",
          height: props.height + "rpx"
        } : ""),
        d: common_vendor.n(props.vertical ? `mx-${props.margin[0]}` : `my-${props.margin[1]}`)
      } : {}, {
        e: _label.value && !props.vertical
      }, _label.value && !props.vertical ? common_vendor.e({
        f: common_vendor.s(tmcomputed.value ? {
          backgroundColor: _realColor.value ? tmcomputed.value.color : tmcomputed.value.border,
          height: props.border + "rpx"
        } : ""),
        g: common_vendor.n(`my-${props.margin[1]}`),
        h: common_vendor.n(__props.align == "left" ? "flex-2" : ""),
        i: common_vendor.n(__props.align == "right" ? "flex-10" : ""),
        j: common_vendor.n(__props.align == "center" ? "flex-1" : ""),
        k: _label.value
      }, _label.value ? {
        l: common_vendor.p({
          fontSize: props.fontSize,
          dark: isDark.value,
          followTheme: props.followTheme,
          color: props.fontColor,
          label: props.label,
          _class: ["mx-32"]
        }),
        m: common_vendor.n(isDark.value ? "opacity-4" : "")
      } : {}, {
        n: common_vendor.s(tmcomputed.value ? {
          backgroundColor: _realColor.value ? tmcomputed.value.color : tmcomputed.value.border,
          height: props.border + "rpx"
        } : ""),
        o: common_vendor.n(`my-${props.margin[1]}`),
        p: common_vendor.n(__props.align == "left" ? "flex-10" : ""),
        q: common_vendor.n(__props.align == "right" ? "flex-2" : ""),
        r: common_vendor.n(__props.align == "center" ? "flex-1" : "")
      }) : {}, {
        s: !_label.value && !props.vertical
      }, !_label.value && !props.vertical ? {
        t: common_vendor.n(`my-${props.margin[1]}`),
        v: common_vendor.s(tmcomputed.value ? {
          backgroundColor: _realColor.value ? tmcomputed.value.color : tmcomputed.value.border,
          height: props.border + "rpx"
        } : "")
      } : {}, {
        w: common_vendor.o(onClick)
      });
    };
  }
});
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "D:/EquipmentManagerDev/EquipManager/wxapp-front/src/tmui/components/tm-divider/tm-divider.vue"]]);
wx.createComponent(Component);
