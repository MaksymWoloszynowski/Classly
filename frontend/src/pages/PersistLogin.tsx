import { useEffect, useState } from "react";
import useAuth from "../hooks/useAuth";
import { Outlet } from "react-router-dom";
import api from "../api/api";
import type { Profile } from "../types/profile";
import LoadingOverlay from "../components/loadingOverlay/LoadingOverlay";

const PersistLogin = () => {
  const { setAuth } = useAuth();
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchMe = async () => {
      try {
        await api.post("/auth/refresh", { withCredentials: true });
        const res = await api.get<Profile>("/api/my-profile", { withCredentials: true });
        setAuth(res.data); 
      } catch (error) {
        console.log(error);
        setAuth(null);
      } finally {
        setLoading(false);
      }
    };

    fetchMe();
  }, []);

  if (loading) return <LoadingOverlay />;

  return <Outlet />;
};

export default PersistLogin;