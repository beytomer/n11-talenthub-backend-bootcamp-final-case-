import { Input, Stack, FormControl, InputLabel } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import { useState, useEffect } from 'react';

const columns = [
  { field: 'id', headerName: 'ID', width: 200 },
  { field: 'name', headerName: 'NAME', width: 200 },
  { field: 'location', headerName: 'LOCATION', width: 200 },
];

export default function Recommendded() {
  const [data, setData] = useState([]);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    axios
      .get(
        `http://localhost:8080/api/v1/restaurants/RecommendRestaurants/${userId}`,
      )
      .then((res) => {
        setData(res.data.data);
      })
      .catch(() => {
        setData([]);
      });
  }, [userId]);

  return (
    <Stack spacing={8}>
      <Stack width={100}>
        <FormControl>
          <InputLabel htmlFor='userId'>UserId</InputLabel>
          <Input
            id='userId'
            value={userId}
            onChange={(e) => {
              setUserId(+e.target.value);
            }}
          />
        </FormControl>
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
