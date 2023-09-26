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
import EditProgramModal from "./EditProgramModal";


export default function ProgramsTableComponent({programs, onChange}) {

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);

    function onEdit(program) {
        console.log('Edit program: ', program);
        setSelectedProgram(program);
        setIsEditModalOpen(true);
    }

    function onDelete(program) {
        console.log('I am going to delete program: ', program);
        fetch(`/programs/${program.id}`,
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
                    onChange(programs.filter(c => c.id !== program.id));
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
                            <TableCell align="right">Kind</TableCell>
                            <TableCell align="right">Duration</TableCell>
                            <TableCell align="right">Price</TableCell>
                            <TableCell align="right">Actions</TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {programs.map((program) => (
                            <TableRow
                                key={program.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {program.id}
                                </TableCell>
                                <TableCell align="right">{program.kind}</TableCell>
                                <TableCell align="right">{program.duration}</TableCell>
                                <TableCell align="right">{program.price}</TableCell>

                                <TableCell align="right">
                                    <Button variant="contained" color="primary" onClick={() => onEdit(program)}>
                                        Edit
                                    </Button>
                                    <span className="inline-block w-4"></span> {/* This creates space */}
                                    <Button variant="contained" color="primary"
                                            onClick={() => onDelete(program, programs)}>
                                        Delete
                                    </Button>
                                </TableCell>

                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {selectedProgram && (
                <EditProgramModal
                    isOpen={isEditModalOpen}
                    onClose={handleCloseEditModal}
                    clientData={selectedProgram}
                    onSave={onChange}
                />
            )}

            <div className="relative h-32 flex flex-nowrap">
                <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
                    {(isSuccessfulDelete === true) && <Alert severity="success">The program deleted successful!</Alert>}
                </div>
            </div>
        </React.Fragment>
    );
}