export const getProgressPercentage = (openCount, closedCount) => (closedCount / (openCount + closedCount)).toFixed(2);
