import React from 'react';
import { Navigate } from 'react-router-dom';

export default function ProtectedRoute({ children }) {
    //TODO: 토큰을 기반으로 로그인 상태 확인
    const isUserAuthenticated = sessionStorage.getItem('storeid');

    if (!isUserAuthenticated) return <Navigate to="/members/login" />;

    return children;
}
