import { Stack } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import { useState, useEffect } from 'react';
import AddRestaurant from './add-restaurant';
import UpdateRestaurant from './update-restaurant';
import DeleteRestaurant from './delete-restaurant';
import Recommendded from './recommended';

const columns = [
  { field: 'id', headerName: 'ID', width: 200 },
  { field: 'name', headerName: 'NAME', width: 200 },
  { field: 'location', headerName: 'LOCATION', width: 200 },
];

export default function RestaurantTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/restaurants').then((res) => {
      setData(res.data.data);
    });
  }, []);

  return (
    <Stack spacing={8}>
      <Stack direction={'row'} spacing={8}>
        <AddRestaurant></AddRestaurant>
        <UpdateRestaurant></UpdateRestaurant>
        <DeleteRestaurant></DeleteRestaurant>
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
      <Recommendded></Recommendded>
    </Stack>
  );
}
