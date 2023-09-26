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

export default function EditGymModal({isOpen, onClose, clientData, onSave}) {
    const [editedData, setEditedData] = useState({...clientData});
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
        setEditedData({...clientData}); // Update local state when the clientData prop changes
    }, [clientData]);

    const handleSave = () => {
        clientData.name = editedData.name;
        clientData.address = editedData.address;
        clientData.county = editedData.county;
        clientData.countyId = editedData.countyId;


        fetch(`/gyms/${clientData.id}`,
            {
                method: 'PUT',
                body: JSON.stringify(clientData),
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then((data) => {
                setIsSuccessAlertOpen(true);
                setTimeout(() => {
                    setIsSuccessAlertOpen(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Error while calling the API:', error);
            });
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setEditedData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
        if (name === 'countyId') {
            let county = countiesData.find(c => c.id === value);
            console.log('county: ', county);
            setEditedData((prevData) => ({
                ...prevData,
                ['county']: county,
            }));
        }

    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>Edit Gym</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Name"
                        name="name"
                        value={editedData.name}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Address"
                        name="address"
                        value={editedData.address}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="County"
                        name="countyId"
                        value={editedData.countyId || editedData.county.id}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
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
                    {isSuccessAlertOpen && <Alert severity="success">The client update was successful!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}