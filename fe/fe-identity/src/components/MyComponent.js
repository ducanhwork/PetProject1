import React from "react";
import { useState } from "react";
import axios from "axios";

const MyComponent = () => {
  const [data, setData] = useState(null);
  const onClick = async () => {
    try {
      const response = await axios.get("http://localhost:");
      setData(response.data);
    } catch (e) {
      console.log(e);
    }
  };
  return (
    <div>
      <button onClick={onClick}>불러오기</button>
      {data && (
        <textarea
          rows={7}
          value={JSON.stringify(data, null, 2)}
          readOnly={true}
        />
      )}
    </div>
  );
};
export default MyComponent;
