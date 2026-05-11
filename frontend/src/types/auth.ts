export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string; 
}

export interface RegisterResponse { 
    id: number;
    email: string;
    firstName: string;
    lastName: string; 
}

export interface LoginRequest {
  email: string;
  password: string;
}  

export interface LoginResponse {
    token: string;
    email: string;
    firstName: string;
}