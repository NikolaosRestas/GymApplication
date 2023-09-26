import React, { useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Alert, Button } from "@mui/material";
import EditCountyModal from "./EditCountyModal";

export default function CountiesTableComponent({ counties, onChange }) {
    const [isEditModalOpen, setIsEditModalOpen] = useState({});
    const [selectedCounty, setSelectedCounty] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    const handleEditModalOpen = (county) => {
        setSelectedCounty(county);
        setIsEditModalOpen(true);
    };

    const handleDelete = (county) => {
        fetch(`/counties/${county.id}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        })
            .then(response => {
                if (response.ok) {
                    setIsSuccessfulDelete({ countyName: county.name });
                    setTimeout(() => {
                        setIsSuccessfulDelete(false);
                    }, 5000);
                    onChange(counties.filter(c => c.id !== county.id));
                }
            });
    };

    const handleEditModalClose = () => {
        setIsEditModalOpen(false);
    };

    return (
        <React.Fragment>
            <TableContainer component={Paper} className="shadow-lg rounded-lg">
                <Table className="min-w-max" aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell className="font-bold">Id</TableCell>
                            <TableCell align="right" className="font-bold">Name</TableCell>
                            <TableCell align="right" className="font-bold">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {counties.map((county) => (
                            <TableRow
                                key={county.id}
                                className="hover:bg-gray-100"
                            >
                                <TableCell component="th" scope="row" className="py-3">
                                    {county.id}
                                </TableCell>
                                <TableCell align="right" className="py-3">{county.name}</TableCell>
                                <TableCell align="right" className="py-3">
                                    <Button className="mr-2" variant="contained" color="primary" onClick={() => handleEditModalOpen(county)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary" onClick={() => handleDelete(county)}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedCounty && (
                <EditCountyModal
                    isOpen={isEditModalOpen}
                    onClose={handleEditModalClose}
                    clientData={selectedCounty}
                />
            )}

            {isSuccessfulDelete && (
                <div className="relative h-32 flex flex-nowrap">
                    <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                        <Alert severity="success">
                            The county {isSuccessfulDelete.countyName} was deleted successfully!
                        </Alert>
                    </div>
                </div>
            )}
        </React.Fragment>
    );
}
