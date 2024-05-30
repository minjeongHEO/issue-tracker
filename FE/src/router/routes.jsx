import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Join from '../components/members/Join';
import Login from '../components/members/Login';
import Index from '../components/Index';
import NotFound from '../components/NotFound';
import MilestoneMain from '../components/milestones/Main';
import LabelMain from '../components/labels/Main';
import ProtectedRoute from './ProtectedRoute';
import NewIssue from '../components/issues/NewIssue';
import IssueDetail from '../components/issues/IssueDetail';

export const AppRoutes = () => {
    return (
        <Router>
            <Routes>
                <Route path="/members/login" element={<Login />} />
                <Route path="/members/join" element={<Join />} />
                <Route
                    path="/milestones"
                    element={
                        <ProtectedRoute>
                            <MilestoneMain />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/labels"
                    element={
                        <ProtectedRoute>
                            <LabelMain />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/issues/new"
                    element={
                        <ProtectedRoute>
                            <NewIssue />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/issues/:issueId"
                    element={
                        <ProtectedRoute>
                            <IssueDetail />
                        </ProtectedRoute>
                    }
                />
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
