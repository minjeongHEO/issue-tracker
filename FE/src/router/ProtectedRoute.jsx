import React from "react";

export default function ProtectedRoute({ children }) {
    // 클라이언트에서 토큰 유효성 검증 (로컬스토리지?)

    const isUserAuthenticated = true; /* 로그인 상태 검증 로직 */

    if (!isUserAuthenticated) {
        return <Navigate to="/login" />;
    }

    return children;
}
