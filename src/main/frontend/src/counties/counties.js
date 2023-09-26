import React, { useEffect, useState } from 'react';
import CountiesTableComponent from './CountiesTableComponent';
import { Button } from '@mui/material';
import NewCountyModal from './NewCountyModal';
import Loader from "../loader/loader";

const CountiesPage = () => {
    const [countiesData, setCountiesData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isNewCountyModalOpen, setNewCountyModalOpen] = useState(false);

    useEffect(() => {
        fetch('/counties')
            .then(response => response.json())
            .then(data => {
                setCountiesData(data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('Error fetching counties:', error);
                setIsLoading(false); // Ensure loading state is updated even on error
            });
    }, []);

    const newCounty = () => {
        setNewCountyModalOpen(true);
    };

    const handleCloseNewCountyModal = () => {
        setNewCountyModalOpen(false);
    };

    const handleSaveNewCounty = county => {
        setCountiesData(prevCounties => [...prevCounties, county]);
        handleCloseNewCountyModal();
    };

    return (
        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Counties</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary" onClick={newCounty}>
                        New County
                    </Button>
                </div>

                {isLoading ? (
                    <Loader />
                ) : (
                    <CountiesTableComponent counties={countiesData} onChange={setCountiesData} />
                )}
            </div>

            {isNewCountyModalOpen && (
                <NewCountyModal
                    isOpen={isNewCountyModalOpen}
                    onClose={handleCloseNewCountyModal}
                    onSave={handleSaveNewCounty}
                />
            )}
        </div>
    );
};

export default CountiesPage;
