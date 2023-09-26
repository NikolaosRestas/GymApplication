import React, {useEffect, useState} from 'react';
import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    MenuItem,
    Select,
    TextField
} from '@mui/material';

export default function NewGymModal({isOpen, onClose, onSave}) {
    const [gym, setGym] = useState({name: ""});
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [countiesData, setCountiesData] = useState([]);

    useEffect(() => {
        fetch('/counties')
            .then(response => response.json())
            .then(data => {
                setCountiesData(data);
            })
            .catch(error => {
                console.error('Error fetching counties:', error);
            });
    }, []);

    useEffect(() => {
        setGym({...gym}); // Update local state when the clientData prop changes
    }, [gym]);

    const handleSave = () => {
        fetch(`/gyms/add`,
            {
                method: 'POST',
                body: JSON.stringify(gym),
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then((data) => {
                onSave(data);
                setIsSuccessAlertOpen(true);
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });

        console.log("Save changes:", gym);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setGym((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Gym</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Gym Name"
                        name="name"
                        value={gym.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />

                    <TextField
                        label="Gym Address"
                        name="address"
                        value={gym.address}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />

                    <Select
                        label="County"
                        name="countyId"
                        value={gym.countyId || 'default'}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        <MenuItem value="default" disabled>Select a County</MenuItem>
                        {
                            countiesData.map((county) => (
                                <MenuItem key={county.id} value={county.id}> {county.name} </MenuItem>))
                        }
                    </Select>


                </DialogContent>

                <DialogActions>
                    <Button onClick={handleCancel}>Cancel</Button>
                    <Button onClick={handleSave} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </Dialog>

            <div className="relative h-32 flex flex-nowrap">
                <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                    {isSuccessAlertOpen && <Alert severity="success">The gym added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}