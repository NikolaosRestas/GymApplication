import React, {useEffect, useState} from 'react';
import {Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Alert,Select,MenuItem} from '@mui/material';

export default function EditCustomerModal({isOpen, onClose, clientData, onSave}) {
    const [editedData, setEditedData] = useState({...clientData});
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [gymsData, setGymsData] = useState ([]);

    useEffect(() => {
        fetch('/gyms')
            .then(response => response.json())
            .then(data => {
                setGymsData(data);
            })
            .catch(error => {
                console.error('Error fetching gyms:', error);
            });
    }, []);

    useEffect(() => {
        setEditedData({...clientData}); // Update local state when the clientData prop changes
    }, [clientData]);

    const handleSave = () => {
        clientData.name = editedData.name;
        clientData.address = editedData.address;
        clientData.email = editedData.email;
        clientData.phone = editedData.phone;

        fetch(`/customers/${clientData.id}`,
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

        console.log("Save changes:", clientData);
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
        if (name === 'gymId') {
            let gym = gymsData.find(c => c.id === value);
            console.log('gym: ', gym);
            setEditedData((prevData) => ({
                ...prevData,
                ['gym']: gym,
            }));
        }

    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>Edit Customer</DialogTitle>
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
                    <TextField
                        label="Email"
                        name="email"
                        value={editedData.email}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Phone"
                        name="phone"
                        value={editedData.phone}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <Select
                        label="Gym"
                        name="gymId"
                        value={editedData.gymId || editedData.gym.id}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    >
                        {
                            gymsData.map((gym) => (
                                <MenuItem key={gym.id} value={gym.id}> {gym.name} </MenuItem>))
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
