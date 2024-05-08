import { useState } from "react";
import "./css/App.css";
import { AppRoutes } from "./router/routes";

function App() {
    const [count, setCount] = useState(0);

    return (
        <div>
            <AppRoutes />
        </div>
    );
}

export default App;
