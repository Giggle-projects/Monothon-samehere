import {ReactComponent as UploadIcon} from "../static/svg/uploadIcon.svg";

const ProfileImageInput = ({...restProps}) => {
  return <>
    <div className="profile-upload">
      <div className="left">
        <div className="title">프로필 사진 업로드</div>
        <div className="description">
          사진이 없으면 기본 프로필로 등록되어요!
        </div>
      </div>
      <label className="upload-button">
        <input
            type="file"
            accept="image/*"
            {...restProps}
        />
        <UploadIcon/>
      </label>
    </div>
  </>
}

export default ProfileImageInput;