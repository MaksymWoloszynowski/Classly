import type { UserRole } from "../enums";

export type AccessCode = {
    id: string;
    refId: string;
    name: string;
    code: string
    role: UserRole;
    used: boolean;
    expiresAt: string;
}