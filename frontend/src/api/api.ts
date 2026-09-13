import axios, { type AxiosError, type InternalAxiosRequestConfig } from "axios";

type RetriableRequestConfig = InternalAxiosRequestConfig & {
  _retry?: boolean;
};

const api = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
});

let refreshRequest: Promise<void> | null = null;

const refreshAccessToken = () => {
  if (!refreshRequest) {
    refreshRequest = api.post<void>("/auth/refresh").then(() => undefined).finally(() => {
      refreshRequest = null;
    });
  }

  return refreshRequest;
};

api.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as RetriableRequestConfig | undefined;
    const requestUrl = originalRequest?.url ?? "";
    const isAuthRequest = requestUrl.startsWith("/auth/");
    const shouldRefresh = error.response?.status === 401 || error.response?.status === 403;

    if (!originalRequest || !shouldRefresh || originalRequest._retry || isAuthRequest) {
      return Promise.reject(error);
    }

    originalRequest._retry = true;
    await refreshAccessToken();
    return api(originalRequest);
  },
);

export default api;
