export interface AuthLoginRequest {
  username: string;
  password: string;
}

export interface AuthLoginResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  username: string;
  roles: string[];
}

export interface AuthSession {
  accessToken: string;
  username: string;
  roles: string[];
}
