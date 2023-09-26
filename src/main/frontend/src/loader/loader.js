import React from 'react';

const Loader = () => {
    return (
        <div className="flex justify-center items-center h-screen fixed top-0 left-0 right-0 bottom-0 bg-opacity-50 bg-gray-200 z-50">
            <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-blue-500"></div>
        </div>
    );
};

export default Loader;
