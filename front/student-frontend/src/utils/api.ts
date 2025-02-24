import axios from 'axios';
import { getSession } from 'next-auth/react'; // Utilisé pour récupérer le token depuis NextAuth

const api = axios.create({
  baseURL: 'http://localhost:8889',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Intercepteur pour ajouter le Bearer Token
api.interceptors.request.use(
  async (config) => {
    // Récupère la session et le token de NextAuth
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const session:any = await getSession();
    const token = session?.access_token; // Adaptez cette ligne selon votre configuration

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    console.log("config: ",config)
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;