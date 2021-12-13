import CryptoJS from "crypto-js";
import { Base64 } from 'js-base64';

/**
 * 对原始参数加密并重新组合.
 * @param {json} param - 接口请求参数明文
 * @return 加密封装后的参数
 */
export const fdAES=(param)=>{
  const LOGIN_KEY = "40C2A8DA4514D3DD70051044B397E22C";
  const $key = CryptoJS.enc.Latin1.parse(LOGIN_KEY);
  let LOGIN_IV = CryptoJS.lib.WordArray.random(64 / 8).toString();
  let $iv = CryptoJS.enc.Latin1.parse(LOGIN_IV);

  let plaintText = JSON.stringify(param);
  let $value = CryptoJS.AES.encrypt(plaintText, $key, {iv: $iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Pkcs7});
  $value = $value.toString();
  $iv = Base64.encode(LOGIN_IV);
  let ivVal = $iv + $value;
  let $mac = CryptoJS.HmacSHA256(ivVal,LOGIN_KEY).toString();
  let obj = {
    iv:$iv,
    value:$value,
    mac:$mac
  };
  let encryptedBase64Str = Base64.encode(JSON.stringify(obj));
  return encryptedBase64Str;
};