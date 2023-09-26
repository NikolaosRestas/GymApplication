import React, {useEffect, useState} from 'react';
import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    MenuItem,
    Select,
    OutlinedInput,
    Checkbox, InputLabel
} from '@mui/material';

export default function NewProgramModal({isOpen, onClose, onSave}) {
    const [program, setProgram] = useState({name: ""});
    const [isSuccessAlertOpen, setIsSuccessAlertOpen] = useState(false);
    const [customersData, setCustomersData] = useState([]);
    const [selectedCustomers, setSelectedCustomers] = useState([]);

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

    useEffect(() => {
        setProgram({...program}); // Update local state when the clientData prop changes
    }, [program]);

    const handleSave = () => {
        fetch(`/programs/add`,
            {
                method: 'POST',
                body: JSON.stringify(program),
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

        console.log("Save changes:", program);
        onClose(); // Close the modal after saving (you can modify this based on your requirements).
    };

    const handleCancel = () => {
        onClose(); // Close the modal without saving.
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setProgram((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleCustomerSelectChange = (event) => {
        setSelectedCustomers(event.target.value);
        setProgram((prevData) => ({
            ...prevData,
            customerIds: event.target.value,
        }));
    };

    return (
        <React.Fragment>
            <Dialog open={isOpen} onClose={onClose}>
                <DialogTitle>New Program</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Kind"
                        name="kind"
                        value={program.kind}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Duration"
                        name="duration"
                        value={program.duration}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Price"
                        name="price"
                        value={program.price}
                        onChange={(e) => handleInputChange(e)}
                        fullWidth
                        margin="normal"
                    />
                    <div>
                        <InputLabel id="demo-multiple-name-label">Customers</InputLabel>
                        <Select
                            label="Customer"
                            name="customerId"
                            value={selectedCustomers}
                            onChange={handleCustomerSelectChange}
                            multiple
                            input={<OutlinedInput label="Customer"/>}
                            fullWidth
                            margin="normal"
                        >
                            {customersData.map((customer) => (
                                <MenuItem key={customer.id} value={customer.id}>
                                    <Checkbox checked={selectedCustomers.includes(customer.id)}/>
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
                    {isSuccessAlertOpen && <Alert severity="success">The program added successfully!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}