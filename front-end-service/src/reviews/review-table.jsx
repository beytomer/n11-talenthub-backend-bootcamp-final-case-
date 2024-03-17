import { Stack } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import { useState, useEffect } from 'react';
import AddReview from './add-review';
import UpdateReview from './update-review';
import DeleteReview from './delete-review';

const columns = [
  { field: 'id', headerName: 'ID', width: 200 },
  { field: 'rate', headerName: 'RATE', width: 200 },
  { field: 'comment', headerName: 'COMMENT', width: 200 },
];

export default function ReviewTable() {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/api/v1/reviews').then((res) => {
      setData(res.data.data);
    });
  }, []);

  return (
    <Stack spacing={8}>
      <Stack direction={'row'} spacing={8}>
        <AddReview></AddReview>
        <UpdateReview></UpdateReview>
        <DeleteReview></DeleteReview>
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
