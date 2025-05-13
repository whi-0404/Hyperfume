import api from './axiosConfig';

const redirectUri = "http://localhost:3000/Sign-in" ;
const clientId = "594721364146-58ila8lpn844h8jo6jgl018lvlo3t928.apps.googleusercontent.com";
const authUrl =  "https://accounts.google.com/o/oauth2/auth";

export const targetGoogleUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
    redirectUri
  )}&response_type=code&client_id=${clientId}&scope=openid%20email%20profile`;


// export const loginGoogle = async () => {
//     try {
//         const response = await api.post('/auth/outbound/authentication',{
//             code: code
//         });
//         return response.data;
//     } catch (error) {
//         console.error('Error logout google:', error);
//         throw error;
//     }
// }



export const exchangeTokenGoogle = async (code) => {
    try {
        const response = await api.post('/auth/outbound/authentication', null, {
            params: { code }
        });
        return response.data;
    } catch (error) {
        console.error('Error exchange token google:', error);
        throw error;
    }
}