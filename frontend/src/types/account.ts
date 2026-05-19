export type Currency = 'PLN';

export interface AccountResponse {
  id: number;
  accountNumber: string;
  balance: number;
  currency: Currency;
  createdAt: string;
}