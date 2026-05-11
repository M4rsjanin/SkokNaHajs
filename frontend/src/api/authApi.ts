import {apiClient} from './clients';

import type {RegisterRequest, RegisterResponse, LoginRequest, LoginResponse} from '@/types/auth';

export const authApi = {
    register: async (data: RegisterRequest): Promise<RegisterResponse> => {
        const response = await apiClient.post<RegisterResponse>('/auth/register', data);
        return response.data;
    },

    login: async (data: LoginRequest): Promise<LoginResponse> => {
        const response = await apiClient.post<LoginResponse>('/auth/login', data);
        return response.data;
    }
}