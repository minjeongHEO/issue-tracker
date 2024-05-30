import React, { useEffect } from 'react';

export default function AuthLoadingPage() {
    useEffect(() => {
        const url = new URL(window.location.href);
        const authorizationCode = url.searchParams.get('code');

        console.log(authorizationCode); //인증 코드
    }, []);

    return <div>GitHub OAuth 로그인 처리 중...</div>;
}
