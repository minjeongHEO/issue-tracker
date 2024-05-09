import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Join from '../components/Join';
import Login from '../components/Login';
import Index from '../components/Index';
import NotFound from '../components/NotFound';
import ProtectedRoute from './ProtectedRoute';

export const AppRoutes = () => {
    return (
        <Router>
            <Routes>
                <Route path="/members/login" element={<Login />} />
                <Route path="/members/join" element={<Join />} />
                <Route
                    path="/"
                    element={
                        <ProtectedRoute>
                            <Index />
                        </ProtectedRoute>
                    }
                />
                <Route path="*" element={<NotFound />} />
            </Routes>
        </Router>
    );
};
