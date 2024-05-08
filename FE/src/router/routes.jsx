import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Join from '../components/Join';
import Login from '../components/Login';
import Main from '../components/Main';
import NotFound from '../components/NotFound';
import ProtectedRoute from './ProtectedRoute';

export const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Main />
            </ProtectedRoute>
          }
        />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};
