import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext.tsx";
import { StudentScopeProvider } from "./context/StudentScopeContext.tsx";
import { TeacherProvider } from "./context/TeacherContext.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <TeacherProvider>
          <StudentScopeProvider>
            <Routes>
              <Route path="/*" element={<App />} />
            </Routes>
          </StudentScopeProvider>
        </TeacherProvider>
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
);
