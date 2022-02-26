import {ReactComponent as UploadIcon} from "../static/svg/uploadIcon.svg";

const ProfileImageInput = (props) => {

  const handleChange = e => {
    e.target.files[0] && props.setFile(e.target.files[0]);
  };

  return <>
    <div className="profile-upload">
      <div className="left">
        <div className="title">프로필 사진 업로드</div>
        <div className="description">
          사진이 없으면 기본 프로필로 등록되어요!
        </div>
      </div>
      <img src={props.file ? URL.createObjectURL(props.file) : './favicon.ico'} alt='profile' />
      <label className="upload-button">
        <input
          type="file"
          accept="image/*"
          onChange={ handleChange }
        />
        <UploadIcon/>
      </label>
    </div>
  </>
}

export default ProfileImageInput;