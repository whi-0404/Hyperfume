import api from './axiosConfig';

export const createShippingAddress = async (addressData) => {
    try {
        const response = await api.post(`/users/shipping_address`, {
            recipientName: addressData.recipientName,
            phone: addressData.phone,
            shipAddress: addressData.shipAddress,
            isDefault: addressData.isDefault
        });
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error create shipping address', error);
        throw error;
    }
}

export const updateShippingAddress = async (shippingAddressId, addressData) => {
    try {
        const response = await api.put(`/users/shipping_address/${shippingAddressId}`, {
            recipientName: addressData.recipientName,
            phone: addressData.phone,
            shipAddress: addressData.shipAddress,
            isDefault: addressData.isDefault
        });
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error update shipping address', error);
        throw error;
    }
}

export const deleteShippingAddress = async (shippingAddressId) => {
    try {
        const response = await api.delete(`/users/shipping_address/${shippingAddressId}`);
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error delete shipping address', error);
        throw error;
    }
}

export const setShippingAddressDefault = async (shippingAddressId) => {
    try {
        const response = await api.put(`/users/shipping_address/setDefault/${shippingAddressId}`);
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error set shipping address default', error);
        throw error;
    }
}

export const getShippingAddress = async () => {
    try {
        const response = await api.get('/users/shipping_address');
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error get shipping address', error);
        throw error;
    }
}