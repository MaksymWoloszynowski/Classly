import { useNavigate } from "react-router-dom";
import api from "../api/api";
import useAuth from "./useAuth";

const useLogout = () => {
    const { setAuth } = useAuth();
    const navigate = useNavigate();

    const logout = async () => {
        try {
            await api.post('/auth/logout');
            navigate('/login');
        } catch (err) {
            console.error(err);
        } finally {
            setAuth(null);
        }
    }

    return logout;
}

export default useLogout
