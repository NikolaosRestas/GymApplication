import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {Alert, Button} from "@mui/material";
import {useState} from "react";
import EditGymModal from "./EditGymModal";


export default function GymsTableComponent({gyms, onChange}) {

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedGym, setSelectedGym] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    function onEdit(gym) {
        console.log('Edit gym: ', gym);
        setSelectedGym(gym);
        setIsEditModalOpen(true);
    }

    function onDelete(gym) {
        console.log('I am going to delete gym: ', gym);
        fetch(`/gyms/${gym.id}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (response.ok) {
                    setIsSuccessfulDelete(true);
                    setTimeout(() => {
                        setIsSuccessfulDelete(false);
                    }, 5000);
                    onChange(gyms.filter(c => c.id !== gym.id));
                }
            });
    }


    const handleCloseEditModal = () => {
        setIsEditModalOpen(false);
    };

    return (
        <React.Fragment>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Id</TableCell>
                            <TableCell align="right">Name</TableCell>
                            <TableCell align="right">Address</TableCell>
                            <TableCell align="right">County</TableCell>
                            <TableCell align="right">Actions</TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {gyms.map((gym) => (
                            <TableRow
                                key={gym.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {gym.id}
                                </TableCell>
                                <TableCell align="right">{gym.name}</TableCell>
                                <TableCell align="right">{gym.address}</TableCell>
                                <TableCell align="right">{gym.county.name}</TableCell>

                                <TableCell align="right">
                                    <Button variant="contained" color="primary" onClick={() => onEdit(gym)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary"
                                            onClick={() => onDelete(gym, gyms)}>
                                        Delete
                                    </Button>
                                </TableCell>

                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedGym && (
                <EditGymModal
                    isOpen={isEditModalOpen}
                    onClose={handleCloseEditModal}
                    clientData={selectedGym}
                    onSave={onChange}
                />
            )}

            <div className="relative h-32 flex flex-nowrap">
                <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                    {(isSuccessfulDelete === true) && <Alert severity="success">The gym deleted successful!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}