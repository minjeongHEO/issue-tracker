import React, { createContext, useEffect, useState } from 'react';
import theme from '../styles/theme.js';

const { lightTheme, darkTheme } = theme;

const initDarkMode = () => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme ? savedTheme === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
};

export const DarkModeContext = createContext();

export default function DarkModeProvider({ children }) {
    const [isDarkMode, setIsDarkMode] = useState(initDarkMode());
    const [darkModeTheme, setDarkModeTheme] = useState(lightTheme);

    useEffect(() => {
        const darkModeType = isDarkMode ? 'dark' : 'light';
        localStorage.setItem('theme', darkModeType);

        if (isDarkMode) setDarkModeTheme(darkTheme);
        else setDarkModeTheme(lightTheme);
    }, [isDarkMode]);

    const toggleDarkMode = () => setIsDarkMode((mode) => !mode);
    return <DarkModeContext.Provider value={{ isDarkMode, toggleDarkMode, darkModeTheme }}>{children}</DarkModeContext.Provider>;
}
