import React, { createContext, useEffect, useState } from 'react';

const initDarkMode = () => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme ? savedTheme === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
};

export const DarkModeContext = createContext();

export default function DarkModeProvider({ children }) {
    const [isDarkMode, setIsDarkMode] = useState(initDarkMode());
    useEffect(() => {
        const darkModeType = isDarkMode ? 'dark' : 'light';
        localStorage.setItem('theme', darkModeType);
    }, [isDarkMode]);

    const toggleDarkMode = () => setIsDarkMode((mode) => !mode);
    return <DarkModeContext.Provider value={{ isDarkMode, toggleDarkMode }}>{children}</DarkModeContext.Provider>;
}
