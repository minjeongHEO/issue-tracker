import { createContext, useContext, useReducer } from 'react';
import { initFilterState, filterReducer } from './filterReducer';

export const FilterContext = createContext();

export default function FilterProvider({ children }) {
    const [state, dispatch] = useReducer(filterReducer, initFilterState);
    return <FilterContext.Provider value={{ state, dispatch }}>{children}</FilterContext.Provider>;
}

export const useFilterContext = () => {
    return useContext(FilterContext);
};
