import { BrowserRouter } from "react-router-dom";
import { Route, Routes } from "react-router";
import Profile from "./pages/Profile";
import Join from "./pages/Join";
import Create from "./pages/create/Create";
import Group from "./pages/Group";
import Edit from "./pages/Edit";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/group/:groupId" element={<Group />} />
        <Route path="/create" element={<Create />} />
        <Route path="/profile/:userId" element={<Profile />} />
        <Route path="/join/:groupId" element={<Join />} />
        <Route path="/edit/:cardId" element={<Edit />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
