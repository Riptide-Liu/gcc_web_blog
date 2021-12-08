import Vue from 'vue'
import CN from './cn'
import VueI18n from 'vue-i18n'

let locale='CN';
Vue.use(VueI18n);

/*---------使用语言包-----------*/
const i18n = new VueI18n({
	locale: locale,	// 语言标识
	messages: {
		'CN': CN,	// 中文语言包
	},
});
export default i18n;