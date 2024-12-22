import { removeToken } from "./authToken";

const logOut = () => {
    removeToken();
    alert('Đăng xuất thành công!');
}

export default logOut;