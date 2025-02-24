import { AuthContext } from 'oidc-react';
import { useContext } from 'react';

export function useAuth() {
  const auth = useContext(AuthContext);
  console.log("auth: ",auth)
  return {
    isAuthenticated: !!auth?.userData,
    user: auth?.userData?.profile || null,
    //roles: auth?.userData?.profile?.realm_access?.roles || [],
    login: () => auth?.signIn(),
    logout: () => auth?.signOut(),
  };
}
