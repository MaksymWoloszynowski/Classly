import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import useLocalePath from "../hooks/useLocalePath";

const RequireAuth = ({ allowedRoles }: { allowedRoles: string[] }) => {
    const { auth } = useAuth();
    const location = useLocation();
    const localePath = useLocalePath();

    return (
        auth !== null && allowedRoles.includes(auth.role)
            ? <Outlet />
            : auth?.userId
                ? <Navigate to={localePath("/unauthorized")} state={{ from: location }} replace />
                : <Navigate to={localePath("/login")} state={{ from: location }} replace />
    );
}

export default RequireAuth;
