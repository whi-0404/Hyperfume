import { toByteArray } from 'base64-js';

const handleBase64Decode = (base64String) => {
    try {
        // Loại bỏ tiền tố nếu có
        const base64 = base64String.split(',')[1] || base64String;

        // Decode Base64 thành Uint8Array
        const byteArray = toByteArray(base64);

        // Tạo Blob từ Uint8Array
        const blob = new Blob([byteArray], { type: 'image/png' });

        // Tạo URL từ Blob
        const imageUrl = URL.createObjectURL(blob);

        return imageUrl;
    } catch (error) {
        console.error('Error decoding Base64:', error.message);
    }
};

export default handleBase64Decode;