import React, {useEffect, useState} from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    Alert,
    MenuItem,
    Select,
    InputLabel, OutlinedInput, Checkbox
} from '@mui/material';

export default function EditProgramModal({isOpen, onClose, clientData, onSave}) {
    const [editedData, setEditedData] = useState(() => {
        const initData = clientData.customers.map(c => c.id);
        const data = {...clientData, customerIds: initData};
        return {...data};
    });
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [customersData, setCustomersData] = useState([]);

    useEffect(() => {
        fetch('/customers')
            .then(response => response.json())
            .then(data => {
                setCustomersData(data);
            })
            .catch(error => {
                console.error('Error fetching customers:', error);
            });
    }, []);

    const handleSave = () => {
        clientData.kind = editedData.kind;
        clientData.duration = editedData.duration;
        clientData.price = editedData.price;
        clientData.customer = editedData.customer;
        clientData.customerIds = editedData.customerIds;

        fetch(`/programs/${clientData.id}`,
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
        if (name === 'customerIds') {
            setEditedData((prevData) => ({
                ...prevData,
                customerIds: event.target.value,
            }));
        }

    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>Edit Program</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Kind"
                        name="kind"
                        value={editedData.kind}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Duration"
                        name="duration"
                        value={editedData.duration}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Price"
                        name="price"
                        value={editedData.price}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <div>
                        <InputLabel>Customers</InputLabel>
                        <Select
                            label="Customer"
                            name="customerIds"
                            value={editedData.customerIds || []}
                            onChange={(e) => handleInputChange(e)}
                            multiple
                            input={<OutlinedInput label="Customers"/>}
                            fullWidth
                            margin="normal"
                        >
                            {customersData.map((customer) => (
                                <MenuItem key={customer.id} value={customer.id}>
                                    <Checkbox checked={editedData.customerIds && editedData.customerIds.includes(customer.id)}/>
                                    {customer.name}
                                </MenuItem>
                            ))
                            }
                        </Select>
                    </div>
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