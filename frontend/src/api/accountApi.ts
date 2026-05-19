import { apiClient } from './clients';
import type { AccountResponse } from '@/types/account';

export const accountApi = {
  getMyAccount: async (): Promise<AccountResponse> => {
    const response = await apiClient.get<AccountResponse>('/accounts/me');
    return response.data;
  },
};