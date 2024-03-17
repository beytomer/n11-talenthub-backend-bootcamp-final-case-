import { Stack } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import { useState, useEffect } from 'react';
import AddUser from './add-user';
import UpdateUser from './update-user';
import DeleteUser from './delete-user';

const columns = [
  { field: 'id', headerName: 'ID', width: 200 },
  { field: 'name', headerName: 'AD', width: 200 },
  { field: 'surname', headerName: 'SOYAD', width: 200 },
  { field: 'gender', headerName: 'GENDER', width: 200 },
  { field: 'birthDate', headerName: 'DOGUM GUNU', width: 200 },
];

export default function UsersTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/api/v1/users').then((res) => {
      setData(res.data.data);
    });
  }, []);

  return (
    <Stack spacing={8}>
      <Stack direction={'row'} spacing={8}>
        <AddUser></AddUser>
        <UpdateUser></UpdateUser>
        <DeleteUser></DeleteUser>
      </Stack>
      <Stack>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: { page: 0, pageSize: 5 },
            },
          }}
          pageSizeOptions={[5, 10]}
        />
      </Stack>
    </Stack>
  );
}
