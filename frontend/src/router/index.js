import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

/* Router Modules */

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login/login'),
    meta: {
      hideInMenu: true,
      notCache: true
    }
  },
  {
    path: '/adminLayout',
    name: 'adminLayout',
    component: () => import('../views/Admin/Layout/layout'),
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: '/adminHome',
        name: 'adminHome',
        component: () => import('../views/Admin/Home/home')
      },
      {
        path: '/userinfo',
        name: 'userinfo',
        component: () => import('../views/Admin/UserInfo/userinfo')
      },
      {
        path: '/user',
        name: 'user',
        component: () => import('../views/Admin/User/user')
      },
      {
        path: '/type',
        name: 'type',
        component: () => import('../views/Admin/Type/type')
      },
      {
        path: '/adminBlog',
        name: 'adminBlog',
        component: () => import('../views/Admin/Blog/blog')
      },
    ]
  },
  {
    path: '/clientLayout',
    name: 'clientLayout',
    component: () => import('../views/Client/Layout/layout'),
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: '/clientHome',
        name: 'clientHome',
        component: () => import('../views/Client/Home/home')
      },
      {
        path: '/clientBlog',
        name: 'clientBlog',
        component: () => import('../views/Client/Blog/blog')
      },
    ]
  },

  // {
  //   path: '/404',
  //   name: 'errorp',
  //   component: () => import('../views/Error-Page/404.vue')
  // },

  {
    path: '/',
    redirect: '/login'
  },

  // {
  //   path: '*',
  //   redirect: '/404',
  //   hidden: true
  // },

]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter();

router.beforeEach((to, from, next) => {
  let name = 'menhu_userinfo';
  // eslint-disable-next-line no-sparse-arrays
  let urlkey = decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null;
  if (urlkey) {
    sessionStorage.setItem('userinfo', urlkey);
  }
  next();
})

router.afterEach()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465

export default router