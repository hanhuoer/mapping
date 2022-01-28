import router from './router'
import {getToken} from '@/utils/auth';

const whiteList = ['/login'];

router.beforeEach(async (to, from, next) => {
    const hasToken = getToken();

    if (hasToken) {
        if (to.path === '/login') {
            next({path: '/'});
        } else {
            next();
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        } else {
            // other pages that do not have permission to access are redirected to the login page.
            next('/login');
        }
    }
});
