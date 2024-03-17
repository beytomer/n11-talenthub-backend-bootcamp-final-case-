import { Stack } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import { useState, useEffect } from 'react';
import AddAddress from './add-address';
import UpdateAddress from './update-address';
import DeleteAddress from './delete-address';

const columns = [
  { field: 'id', headerName: 'ID', width: 200 },
  { field: 'county', headerName: 'COUNTRY', width: 200 },
  { field: 'city', headerName: 'CITY', width: 200 },
  { field: 'location', headerName: 'LOCATION', width: 200 },
];

export default function AddressTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/api/v1/addresses').then((res) => {
      setData(res.data.data);
    });
  }, []);

  return (
    <Stack spacing={8}>
      <Stack direction={'row'} spacing={8}>
        <AddAddress></AddAddress>
        <UpdateAddress></UpdateAddress>
        <DeleteAddress></DeleteAddress>
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
