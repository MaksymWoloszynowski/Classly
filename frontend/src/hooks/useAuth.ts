import { useContext } from "react";
import AuthContext from "../context/AuthContext";

const useAuth = () => {
  const context = useContext(AuthContext);

  if (context === null) {
    throw new Error("AuthContext must be used within a AuthContextProvider");
  }

  return context;
};

export default useAuth;
