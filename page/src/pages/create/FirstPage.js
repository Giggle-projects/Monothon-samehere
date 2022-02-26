import DropDown from "../../components/common/DropDown";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";
import {ReactComponent as UploadIcon} from "../../static/svg/uploadIcon.svg";

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

const FirstPage = (props) => {

  const {
    name,
    gender,
    job,
    email,
    password,
    passwordConfirm
  } = props.essentialAnswers;

  const isFullField =
      name &&
      gender &&
      job &&
      email &&
      password &&
      password === passwordConfirm &&
      true;

  return <>
    <div className="title">
      <>
        편리한 정보 공유를 위해
        <br/> 꼭 입력해주세요!
      </>
    </div>
    <div className="content">
      <ProfileImageInput 
        onChange={(e) => e.target.files && props.setFile(e.target.files[0])}
      />
      <Input
          value={name}
          title="이름"
          placeholder="이름을 입력해주세요."
          name="name"
          onChange={props.onChangeEssentialAnswer}
      />
      <DropDown
          onChange={props.onChangeEssentialAnswer}
          title="성별"
          name="gender"
          placeholder="성별을 선택하세요."
          items={["남", "여"]}
      />

      <Input
          value={job}
          title="직업"
          placeholder="직업을 입력해주세요. ex) Frontend Engineer"
          name="job"
          onChange={props.onChangeEssentialAnswer}
      />
      <Input
          value={email}
          title="이메일"
          placeholder="이메일을 입력해주세요."
          name="email"
          onChange={props.onChangeEssentialAnswer}
      />
      <Input
          title="비밀번호"
          placeholder="비밀번호를 입력해주세요."
          value={password}
          name="password"
          onChange={props.onChangeEssentialAnswer}
      />
      <Input
          title="비밀번호 확인"
          placeholder="비밀번호를 확인해주세요."
          value={props.passwordConfirm}
          name="passwordConfirm"
          onChange={props.onChangeEssentialAnswer}
      />
    </div>
    <div className="bottomButton">
      <Button
          onClick={() => (props.setPage(1))}
          disabled={props.loading ? true : !(isFullField || false)}
      >
        다음 내용을 입력하기
      </Button>
    </div>
  </>;
}

export default FirstPage;
