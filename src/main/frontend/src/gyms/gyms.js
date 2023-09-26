import React, {useEffect, useState} from 'react';
import GymsTableComponent from "./GymsTableComponent";
import {Button} from "@mui/material";
import NewGymModal from "./NewGymModal";

const GymsPage = () => {
    const [gymsData, setGymsData] = useState([]);
    const [isNewGymModalOpen, setNewGymModalOpen] = useState(false);

    useEffect(() => {
        // Function to fetch gyms data from the API
        fetch('/gyms')
            .then(response => response.json())
            .then(data => {
                setGymsData(data);
            });

        return () => {
            // Any cleanup code can go here
        };
    }, []); // Empty dependency array means this effect runs only once when the component mounts

    useEffect(() => {
        console.log('Meta to add!!!! : ',gymsData);
        setGymsData(gymsData); // Update local state when the clientData prop changes
    }, [gymsData]);

    function newGym() {
        setNewGymModalOpen(true);
    }

    const handleCloseNewGymModal = () => {
        setNewGymModalOpen(false);
    };

    const updateGyms = (gym) => {
        console.log('GYM: ', gym);
        console.log('GYMs: ', gymsData);

        setGymsData(prevGyms => [...prevGyms, gym]);
        console.log('GYMs meta: ', gymsData);

    };

    return (

        <div className="flex justify-center">
            <div className="container mx-4 mt-8 w-full max-w-screen-lg">
                <h3 className="text-3xl font-bold mb-4">Gyms</h3>

                <div className="text-right mb-4">
                    <Button variant="contained" color="primary"
                            onClick={() => newGym()}>
                        New Gym
                    </Button>
                </div>

                <GymsTableComponent gyms={gymsData} onChange={setGymsData}/>
            </div>



            {isNewGymModalOpen && (
                <NewGymModal
                    isOpen={isNewGymModalOpen}
                    onClose={handleCloseNewGymModal}
                    onSave={updateGyms}
                />
            )}
        </div>

    );
};

export default GymsPage;